import { IReporte } from 'app/shared/model//reporte.model';
import { IAreaRiesgo } from 'app/shared/model//area-riesgo.model';
import { ITipoRiesgo } from 'app/shared/model//tipo-riesgo.model';

export const enum ValoracionEnum {
    LEVE = 'LEVE',
    MODERADO = 'MODERADO',
    CRITICO = 'CRITICO'
}

export const enum EstadoEnum {
    PENDIENTE = 'PENDIENTE',
    ACEPTADO = 'ACEPTADO',
    RECHAZADO = 'RECHAZADO'
}

export interface IRespuesta {
    id?: number;
    valoracion?: ValoracionEnum;
    descripcion?: any;
    estado?: EstadoEnum;
    reporte?: IReporte;
    areaRiesgos?: IAreaRiesgo[];
    tipoRiesgos?: ITipoRiesgo[];
}

export class Respuesta implements IRespuesta {
    constructor(
        public id?: number,
        public valoracion?: ValoracionEnum,
        public descripcion?: any,
        public estado?: EstadoEnum,
        public reporte?: IReporte,
        public areaRiesgos?: IAreaRiesgo[],
        public tipoRiesgos?: ITipoRiesgo[]
    ) {}
}
