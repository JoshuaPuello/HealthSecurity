import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAreaRiesgo } from 'app/shared/model/area-riesgo.model';
import { AreaRiesgoService } from './area-riesgo.service';

@Component({
    selector: 'jhi-area-riesgo-delete-dialog',
    templateUrl: './area-riesgo-delete-dialog.component.html'
})
export class AreaRiesgoDeleteDialogComponent {
    areaRiesgo: IAreaRiesgo;

    constructor(private areaRiesgoService: AreaRiesgoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.areaRiesgoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'areaRiesgoListModification',
                content: 'Deleted an areaRiesgo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-area-riesgo-delete-popup',
    template: ''
})
export class AreaRiesgoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ areaRiesgo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AreaRiesgoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.areaRiesgo = areaRiesgo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
