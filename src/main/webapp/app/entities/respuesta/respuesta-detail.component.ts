import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRespuesta } from 'app/shared/model/respuesta.model';

@Component({
    selector: 'jhi-respuesta-detail',
    templateUrl: './respuesta-detail.component.html'
})
export class RespuestaDetailComponent implements OnInit {
    respuesta: IRespuesta;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ respuesta }) => {
            this.respuesta = respuesta;
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
