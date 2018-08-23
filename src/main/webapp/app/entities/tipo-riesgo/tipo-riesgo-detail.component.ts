import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';

@Component({
    selector: 'jhi-tipo-riesgo-detail',
    templateUrl: './tipo-riesgo-detail.component.html'
})
export class TipoRiesgoDetailComponent implements OnInit {
    tipoRiesgo: ITipoRiesgo;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoRiesgo }) => {
            this.tipoRiesgo = tipoRiesgo;
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
