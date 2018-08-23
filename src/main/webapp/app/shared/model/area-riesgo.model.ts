import { IRespuesta } from 'app/shared/model//respuesta.model';

export interface IAreaRiesgo {
    id?: number;
    nombre?: string;
    descripcion?: any;
    respuestas?: IRespuesta[];
}

export class AreaRiesgo implements IAreaRiesgo {
    constructor(public id?: number, public nombre?: string, public descripcion?: any, public respuestas?: IRespuesta[]) {}
}
