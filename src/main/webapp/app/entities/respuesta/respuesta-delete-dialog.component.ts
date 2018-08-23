import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRespuesta } from 'app/shared/model/respuesta.model';
import { RespuestaService } from './respuesta.service';

@Component({
    selector: 'jhi-respuesta-delete-dialog',
    templateUrl: './respuesta-delete-dialog.component.html'
})
export class RespuestaDeleteDialogComponent {
    respuesta: IRespuesta;

    constructor(private respuestaService: RespuestaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.respuestaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'respuestaListModification',
                content: 'Deleted an respuesta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-respuesta-delete-popup',
    template: ''
})
export class RespuestaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ respuesta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RespuestaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.respuesta = respuesta;
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
