import { ITema } from 'app/shared/model//tema.model';
import { ICategoria } from 'app/shared/model//categoria.model';

export interface IWiki {
    id?: number;
    titulo?: string;
    imagenContentType?: string;
    imagen?: any;
    descripcion?: any;
    tema?: ITema;
    categoria?: ICategoria;
}

export class Wiki implements IWiki {
    constructor(
        public id?: number,
        public titulo?: string,
        public imagenContentType?: string,
        public imagen?: any,
        public descripcion?: any,
        public tema?: ITema,
        public categoria?: ICategoria
    ) {}
}
