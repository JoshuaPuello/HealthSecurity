import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAreaRiesgo } from 'app/shared/model/area-riesgo.model';
import { AreaRiesgoService } from './area-riesgo.service';
import { IRespuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from 'app/entities/respuesta';

@Component({
    selector: 'jhi-area-riesgo-update',
    templateUrl: './area-riesgo-update.component.html'
})
export class AreaRiesgoUpdateComponent implements OnInit {
    private _areaRiesgo: IAreaRiesgo;
    isSaving: boolean;

    respuestas: IRespuesta[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private areaRiesgoService: AreaRiesgoService,
        private respuestaService: RespuestaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ areaRiesgo }) => {
            this.areaRiesgo = areaRiesgo;
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
        if (this.areaRiesgo.id !== undefined) {
            this.subscribeToSaveResponse(this.areaRiesgoService.update(this.areaRiesgo));
        } else {
            this.subscribeToSaveResponse(this.areaRiesgoService.create(this.areaRiesgo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAreaRiesgo>>) {
        result.subscribe((res: HttpResponse<IAreaRiesgo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get areaRiesgo() {
        return this._areaRiesgo;
    }

    set areaRiesgo(areaRiesgo: IAreaRiesgo) {
        this._areaRiesgo = areaRiesgo;
    }
}
