import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoRiesgo } from 'app/shared/model/tipo-riesgo.model';
import { TipoRiesgoService } from './tipo-riesgo.service';

@Component({
    selector: 'jhi-tipo-riesgo-delete-dialog',
    templateUrl: './tipo-riesgo-delete-dialog.component.html'
})
export class TipoRiesgoDeleteDialogComponent {
    tipoRiesgo: ITipoRiesgo;

    constructor(private tipoRiesgoService: TipoRiesgoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoRiesgoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tipoRiesgoListModification',
                content: 'Deleted an tipoRiesgo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-riesgo-delete-popup',
    template: ''
})
export class TipoRiesgoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoRiesgo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TipoRiesgoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tipoRiesgo = tipoRiesgo;
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
