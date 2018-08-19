export interface ITema {
    id?: number;
    nombre?: string;
    descripcion?: any;
}

export class Tema implements ITema {
    constructor(public id?: number, public nombre?: string, public descripcion?: any) {}
}
