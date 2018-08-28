import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IReporte } from 'app/shared/model/reporte.model';
import { ReporteService } from './reporte.service';
import { IRespuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from 'app/entities/respuesta';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-reporte-update',
    templateUrl: './reporte-update.component.html'
})
export class ReporteUpdateComponent implements OnInit {
    private _reporte: IReporte;
    isSaving: boolean;

    respuestas: IRespuesta[];

    users: IUser[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private reporteService: ReporteService,
        private respuestaService: RespuestaService,
        private userService: UserService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reporte }) => {
            this.reporte = reporte;
        });
        this.respuestaService.query().subscribe(
            (res: HttpResponse<IRespuesta[]>) => {
                this.respuestas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
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
        this.dataUtils.clearInputImage(this.reporte, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reporte.id !== undefined) {
            this.subscribeToSaveResponse(this.reporteService.update(this.reporte));
        } else {
            this.subscribeToSaveResponse(this.reporteService.create(this.reporte));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReporte>>) {
        result.subscribe((res: HttpResponse<IReporte>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get reporte() {
        return this._reporte;
    }

    set reporte(reporte: IReporte) {
        this._reporte = reporte;
    }
}
