export const enum ValoracionEnum {
    LEVE = 'LEVE',
    MODERADO = 'MODERADO',
    CRITICO = 'CRITICO'
}

export const enum EstadoEnum {
    PENDIENTE = 'PENDIENTE',
    RECHAZADO = 'RECHAZADO',
    ACEPTADO = 'ACEPTADO'
}

export interface IRespuesta {
    id?: number;
    valoracion?: ValoracionEnum;
    descripcion?: any;
    estado?: EstadoEnum;
}

export class Respuesta implements IRespuesta {
    constructor(public id?: number, public valoracion?: ValoracionEnum, public descripcion?: any, public estado?: EstadoEnum) {}
}
