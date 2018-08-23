import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IWiki } from 'app/shared/model/wiki.model';
import { WikiService } from './wiki.service';
import { ITema } from 'app/shared/model/tema.model';
import { TemaService } from 'app/entities/tema';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';

@Component({
    selector: 'jhi-wiki-update',
    templateUrl: './wiki-update.component.html'
})
export class WikiUpdateComponent implements OnInit {
    private _wiki: IWiki;
    isSaving: boolean;

    temas: ITema[];

    categorias: ICategoria[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private wikiService: WikiService,
        private temaService: TemaService,
        private categoriaService: CategoriaService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wiki }) => {
            this.wiki = wiki;
        });
        this.temaService.query().subscribe(
            (res: HttpResponse<ITema[]>) => {
                this.temas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoriaService.query().subscribe(
            (res: HttpResponse<ICategoria[]>) => {
                this.categorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.wiki, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.wiki.id !== undefined) {
            this.subscribeToSaveResponse(this.wikiService.update(this.wiki));
        } else {
            this.subscribeToSaveResponse(this.wikiService.create(this.wiki));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWiki>>) {
        result.subscribe((res: HttpResponse<IWiki>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTemaById(index: number, item: ITema) {
        return item.id;
    }

    trackCategoriaById(index: number, item: ICategoria) {
        return item.id;
    }
    get wiki() {
        return this._wiki;
    }

    set wiki(wiki: IWiki) {
        this._wiki = wiki;
    }
}
