import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IWiki } from 'app/shared/model/wiki.model';

@Component({
    selector: 'jhi-wiki-detail',
    templateUrl: './wiki-detail.component.html'
})
export class WikiDetailComponent implements OnInit {
    wiki: IWiki;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
