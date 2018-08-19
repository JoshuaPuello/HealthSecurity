import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';
import { TipoRiesgoService } from './tipo-riesgo.service';
import { IRespuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from 'app/entities/respuesta';

@Component({
    selector: 'jhi-tipo-riesgo-update',
    templateUrl: './tipo-riesgo-update.component.html'
})
export class TipoRiesgoUpdateComponent implements OnInit {
    private _tipoRiesgo: ITipoRiesgo;
    isSaving: boolean;

    respuestas: IRespuesta[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private tipoRiesgoService: TipoRiesgoService,
        private respuestaService: RespuestaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoRiesgo }) => {
            this.tipoRiesgo = tipoRiesgo;
        });
        this.respuestaService.query().subscribe(
            (res: HttpResponse<IRespuesta[]>) => {
                this.respuestas = res.body;
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRespuestaById(index: number, item: IRespuesta) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get tipoRiesgo() {
        return this._tipoRiesgo;
    }

    set tipoRiesgo(tipoRiesgo: ITipoRiesgo) {
        this._tipoRiesgo = tipoRiesgo;
    }
}
