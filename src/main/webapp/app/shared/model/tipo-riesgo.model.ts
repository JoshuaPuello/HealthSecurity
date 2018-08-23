import { IRespuesta } from 'app/shared/model//respuesta.model';

export interface ITipoRiesgo {
    id?: number;
    nombre?: string;
    descripcion?: any;
    respuestas?: IRespuesta[];
}

export class TipoRiesgo implements ITipoRiesgo {
    constructor(public id?: number, public nombre?: string, public descripcion?: any, public respuestas?: IRespuesta[]) {}
}
