import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IWiki } from 'app/shared/model/wiki.model';
import { WikiService } from './wiki.service';

@Component({
    selector: 'jhi-wiki-update',
    templateUrl: './wiki-update.component.html'
})
export class WikiUpdateComponent implements OnInit {
    private _wiki: IWiki;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private wikiService: WikiService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ wiki }) => {
            this.wiki = wiki;
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
    get wiki() {
        return this._wiki;
    }

    set wiki(wiki: IWiki) {
        this._wiki = wiki;
    }
}
