import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAreaRiesgo } from 'app/shared/model/area-riesgo.model';

@Component({
    selector: 'jhi-area-riesgo-detail',
    templateUrl: './area-riesgo-detail.component.html'
})
export class AreaRiesgoDetailComponent implements OnInit {
    areaRiesgo: IAreaRiesgo;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ areaRiesgo }) => {
            this.areaRiesgo = areaRiesgo;
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
