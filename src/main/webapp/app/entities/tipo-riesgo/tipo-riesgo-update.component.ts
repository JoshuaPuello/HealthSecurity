import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';
import { TipoRiesgoService } from './tipo-riesgo.service';

@Component({
    selector: 'jhi-tipo-riesgo-update',
    templateUrl: './tipo-riesgo-update.component.html'
})
export class TipoRiesgoUpdateComponent implements OnInit {
    private _tipoRiesgo: ITipoRiesgo;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private tipoRiesgoService: TipoRiesgoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoRiesgo }) => {
            this.tipoRiesgo = tipoRiesgo;
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
        if (this.tipoRiesgo.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoRiesgoService.update(this.tipoRiesgo));
        } else {
            this.subscribeToSaveResponse(this.tipoRiesgoService.create(this.tipoRiesgo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoRiesgo>>) {
        result.subscribe((res: HttpResponse<ITipoRiesgo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get tipoRiesgo() {
        return this._tipoRiesgo;
    }

    set tipoRiesgo(tipoRiesgo: ITipoRiesgo) {
        this._tipoRiesgo = tipoRiesgo;
    }
}
