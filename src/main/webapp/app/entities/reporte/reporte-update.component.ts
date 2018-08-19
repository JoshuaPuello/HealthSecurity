import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IReporte } from 'app/shared/model/reporte.model';
import { ReporteService } from './reporte.service';

@Component({
    selector: 'jhi-reporte-update',
    templateUrl: './reporte-update.component.html'
})
export class ReporteUpdateComponent implements OnInit {
    private _reporte: IReporte;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private reporteService: ReporteService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reporte }) => {
            this.reporte = reporte;
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
    get reporte() {
        return this._reporte;
    }

    set reporte(reporte: IReporte) {
        this._reporte = reporte;
    }
}
