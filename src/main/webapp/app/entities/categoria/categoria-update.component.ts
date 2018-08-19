import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';

@Component({
    selector: 'jhi-categoria-update',
    templateUrl: './categoria-update.component.html'
})
export class CategoriaUpdateComponent implements OnInit {
    private _categoria: ICategoria;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private categoriaService: CategoriaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categoria }) => {
            this.categoria = categoria;
        });
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.categoria.id !== undefined) {
            this.subscribeToSaveResponse(this.categoriaService.update(this.categoria));
        } else {
            this.subscribeToSaveResponse(this.categoriaService.create(this.categoria));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICategoria>>) {
        result.subscribe((res: HttpResponse<ICategoria>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get categoria() {
        return this._categoria;
    }

    set categoria(categoria: ICategoria) {
        this._categoria = categoria;
    }
}
