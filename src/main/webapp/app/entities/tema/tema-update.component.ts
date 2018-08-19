import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ITema } from 'app/shared/model/tema.model';
import { TemaService } from './tema.service';

@Component({
    selector: 'jhi-tema-update',
    templateUrl: './tema-update.component.html'
})
export class TemaUpdateComponent implements OnInit {
    private _tema: ITema;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private temaService: TemaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tema }) => {
            this.tema = tema;
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
        if (this.tema.id !== undefined) {
            this.subscribeToSaveResponse(this.temaService.update(this.tema));
        } else {
            this.subscribeToSaveResponse(this.temaService.create(this.tema));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITema>>) {
        result.subscribe((res: HttpResponse<ITema>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get tema() {
        return this._tema;
    }

    set tema(tema: ITema) {
        this._tema = tema;
    }
}
