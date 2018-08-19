export interface ITipoRiesgo {
    id?: number;
    nombre?: string;
    descripcion?: any;
}

export class TipoRiesgo implements ITipoRiesgo {
    constructor(public id?: number, public nombre?: string, public descripcion?: any) {}
}
