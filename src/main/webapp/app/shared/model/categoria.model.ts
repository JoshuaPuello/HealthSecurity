export interface ICategoria {
    id?: number;
    nombre?: string;
    descripcion?: any;
}

export class Categoria implements ICategoria {
    constructor(public id?: number, public nombre?: string, public descripcion?: any) {}
}
