export interface IWiki {
    id?: number;
    titulo?: string;
    imagenContentType?: string;
    imagen?: any;
    descripcion?: any;
}

export class Wiki implements IWiki {
    constructor(
        public id?: number,
        public titulo?: string,
        public imagenContentType?: string,
        public imagen?: any,
        public descripcion?: any
    ) {}
}
