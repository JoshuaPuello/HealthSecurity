import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IRespuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from './respuesta.service';

@Component({
    selector: 'jhi-respuesta-update',
    templateUrl: './respuesta-update.component.html'
})
export class RespuestaUpdateComponent implements OnInit {
    private _respuesta: IRespuesta;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private respuestaService: RespuestaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ respuesta }) => {
            this.respuesta = respuesta;
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
        if (this.respuesta.id !== undefined) {
            this.subscribeToSaveResponse(this.respuestaService.update(this.respuesta));
        } else {
            this.subscribeToSaveResponse(this.respuestaService.create(this.respuesta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRespuesta>>) {
        result.subscribe((res: HttpResponse<IRespuesta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get respuesta() {
        return this._respuesta;
    }

    set respuesta(respuesta: IRespuesta) {
        this._respuesta = respuesta;
    }
}
