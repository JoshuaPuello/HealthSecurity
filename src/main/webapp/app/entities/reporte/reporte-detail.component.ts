import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReporte } from 'app/shared/model/reporte.model';

@Component({
    selector: 'jhi-reporte-detail',
    templateUrl: './reporte-detail.component.html'
})
export class ReporteDetailComponent implements OnInit {
    reporte: IReporte;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
