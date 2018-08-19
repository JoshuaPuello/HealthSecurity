export interface IAreaRiesgo {
    id?: number;
    nombre?: string;
    descripcion?: any;
}

export class AreaRiesgo implements IAreaRiesgo {
    constructor(public id?: number, public nombre?: string, public descripcion?: any) {}
}
