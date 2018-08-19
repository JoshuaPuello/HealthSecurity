import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IRespuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from './respuesta.service';
import { IReporte } from 'app/shared/model/reporte.model';
import { ReporteService } from 'app/entities/reporte';
import { IAreaRiesgo } from 'app/shared/model/area-riesgo.model';
import { AreaRiesgoService } from 'app/entities/area-riesgo';
import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';
import { TipoRiesgoService } from 'app/entities/tipo-riesgo';

@Component({
    selector: 'jhi-respuesta-update',
    templateUrl: './respuesta-update.component.html'
})
export class RespuestaUpdateComponent implements OnInit {
    private _respuesta: IRespuesta;
    isSaving: boolean;

    reportes: IReporte[];

    areariesgos: IAreaRiesgo[];

    tiporiesgos: ITipoRiesgo[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private respuestaService: RespuestaService,
        private reporteService: ReporteService,
        private areaRiesgoService: AreaRiesgoService,
        private tipoRiesgoService: TipoRiesgoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ respuesta }) => {
            this.respuesta = respuesta;
        });
        this.reporteService.query({ filter: 'respuesta-is-null' }).subscribe(
            (res: HttpResponse<IReporte[]>) => {
                if (!this.respuesta.reporte || !this.respuesta.reporte.id) {
                    this.reportes = res.body;
                } else {
                    this.reporteService.find(this.respuesta.reporte.id).subscribe(
                        (subRes: HttpResponse<IReporte>) => {
                            this.reportes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.areaRiesgoService.query().subscribe(
            (res: HttpResponse<IAreaRiesgo[]>) => {
                this.areariesgos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoRiesgoService.query().subscribe(
            (res: HttpResponse<ITipoRiesgo[]>) => {
                this.tiporiesgos = res.body;
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackReporteById(index: number, item: IReporte) {
        return item.id;
    }

    trackAreaRiesgoById(index: number, item: IAreaRiesgo) {
        return item.id;
    }

    trackTipoRiesgoById(index: number, item: ITipoRiesgo) {
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
    get respuesta() {
        return this._respuesta;
    }

    set respuesta(respuesta: IRespuesta) {
        this._respuesta = respuesta;
    }
}
