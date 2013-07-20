%por el momento no consideramos distancias entre zonas, ni buscamos optimalidad
%de la solución; con que sea factible basta.

:- use_module(library(clpfd)).

%###control de input###
%		-no pueden haber dos zonas con el mismo id
%		-no pueden haber dos zonas con el mismo id
%		-horario_inicio <= horario_fin
%		-costo de zona siempre positivo
%		-autonomia de autos siempre positiva


%###para que un recorrido sea valido###
%		-un auto no puede recorrer dos zonas distintas si se solapan los horarios
%		-cada zona es recorrida por un solo auto
%		-la suma de los costos de las zonas que recorre un auto debe ser menor que su autonomia
%		-todas las zonas deben ser recorridas

recorridosValidos(Recorridos,    	%lista de recorrido(auto, zona, hora)		(hora de 0 a 23)
					Autos,		 	%lista de auto(id,autonomia,velocidadPromedio)
					Zonas)			%lista de zona(id,costo, horario_inicio, horario_fin)	(hora_inicio es la primera hora consumida, hora_fin es la ultima)
					:-
					asignaAutos(Recorridos, Autos, Zonas),
					asignaHoras(Recorridos).
					

					
%cada zona es recorrida por un solo auto
%la suma de los costos de las zonas que recorre un auto debe ser menor que su autonomia
%todas las zonas deben ser recorridas
%asignaAutos(-Recorridos, +Autos, +Zonas)
asignaAutos(R,_,[]) :- R=[],!.
asignaAutos([Rh|Rt],Autos,[Zh|Zt]) :- autosIdList(Autos, AutosIds),
										member(AutoId, AutosIds),				%asigna auto
										autoDeAutoId(AutoId, Autos, Auto),
										Rh = recorrido(Auto,Zh,_),
										asignaAutos(Rt,Autos,Zt),
										satisfaceAutonomia(Auto, [Rh|Rt]).

										
%autosIdList(+Autos,-IdList) saca una lista de IDs de autos a partir de una lista de autos
autosIdList([],IdList) :- IdList=[].
autosIdList([Ah|At], [Ih|It]) :- autosIdList(At,It), Ah=auto(Id,_,_), Ih = Id.

%autoDeAutoId(+AutoId, +Autos, -Auto)
autoDeAutoId(_,[],_) :- fail.
autoDeAutoId(AutoId, [Ah|_], Ah) :- Ah=auto(AutoId,_,_).
autoDeAutoId(AutoId, [_|At], Auto) :- autoDeAutoId(AutoId, At, Auto).


%satisfaceAutonomia(+Auto, +Recorridos)
satisfaceAutonomia(Auto, Recorridos) :- satisfaceAutonomia(Auto, Recorridos, 0).
satisfaceAutonomia(auto(_,Autonomia,_),[],SumaCosto) :- Autonomia>=SumaCosto,!.
satisfaceAutonomia(Auto, [recorrido(Auto, zona(_,Costo,_,_),_)|Rt],SumaCosto) :- SumaCosto2 is SumaCosto+Costo,
																				!,satisfaceAutonomia(Auto,Rt, SumaCosto2).
satisfaceAutonomia(Auto, [_|Rt], SumaCosto) :- satisfaceAutonomia(Auto,Rt,SumaCosto).


%---------------------------------------------------------------------------------------------------------------------------

%Recorridos es una lista de recorrido(auto, zona, hora), en donde las unicas variables
%todavia libres son las horas de cada recorrido
%asignaHoras(?Recorridos)
asignaHoras([]) :- !.
asignaHoras([ recorrido(auto(_,_,Velocidad),
						zona(_,Distancia,HoraInicio,HoraFin),
						Hora) |Rt])
							:-
						TiempoNecesario is ceil(Distancia/Velocidad),		%se redondea hacia arriba
						Hora in 0..23,										%hora va de 0 a 23
						Hora #>= HoraInicio,
						Hora + TiempoNecesario - 1 #=< HoraFin,
						asignaHoras(Rt).
						
						

%horariosDelAutoNoSeSolapan(+Recorrido, +OtrosRecorridos)	hace que el auto de este recorrido no este en
%															otros lugares dentro del mismo horario.
horariosDelAutoNoSeSolapan(_,[]) :- !.
horariosDelAutoNoSeSolapan(	Recorrido1,	[Recorrido2|Rt])
								:-
								Recorrido1 = recorrido(Auto , zona(_,Distancia1,_,_), Hora1),
								Recorrido2 = recorrido(Auto , zona(_,Distancia2,_,_) ,Hora2),	%si no hablamos del mismo auto, falla aqui
								!,
								Auto = auto(_,_,Velocidad),
								Tiempo1 is ceil(Distancia1/Velocidad),
								Tiempo2 is ceil(Distancia2/Velocidad),
								Hora1 + Tiempo1 #=< Hora2  #\/  Hora2 + Tiempo2 #=< Hora1,
								horariosDelAutoNoSeSolapan(Recorrido1, Rt).
horariosDelAutoNoSeSolapan(R,[_|Rt]) :- horariosDelAutoNoSeSolapan(R,Rt).
								
								
								
%horariosNoSeSolapan(Hora1,Tiempo1,Hora2,Tiempo2) :- Hora1 + Tiempo1 #=< Hora2.
%horariosNoSeSolapan(Hora1,Tiempo1,Hora2,Tiempo2) :- Hora2 + Tiempo2 #=< Hora1.
