export const enum ValoracionEnum {
    LEVE = 'LEVE',
    MODERADO = 'MODERADO',
    CRITICO = 'CRITICO'
}

export const enum LugarEventoEnum {
    DENTRO_EMPRESA = 'DENTRO_EMPRESA',
    FUERA_EMPRESA = 'FUERA_EMPRESA'
}

export const enum TipoEventoEnum {
    ACCIDENTE = 'ACCIDENTE',
    INCIDENTE = 'INCIDENTE'
}

export interface IReporte {
    id?: number;
    valoracion?: ValoracionEnum;
    nombre?: string;
    descripcion?: any;
    acciones_realizadas?: any;
    evidenciaContentType?: string;
    evidencia?: any;
    lugar_evento?: LugarEventoEnum;
    tipo_evento?: TipoEventoEnum;
    bLabores?: boolean;
    bReportado?: boolean;
}

export class Reporte implements IReporte {
    constructor(
        public id?: number,
        public valoracion?: ValoracionEnum,
        public nombre?: string,
        public descripcion?: any,
        public acciones_realizadas?: any,
        public evidenciaContentType?: string,
        public evidencia?: any,
        public lugar_evento?: LugarEventoEnum,
        public tipo_evento?: TipoEventoEnum,
        public bLabores?: boolean,
        public bReportado?: boolean
    ) {
        this.bLabores = false;
        this.bReportado = false;
    }
}
