/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HealthSecurityTestModule } from '../../../test.module';
import { TipoRiesgoDeleteDialogComponent } from 'app/entities/tipo-riesgo/tipo-riesgo-delete-dialog.component';
import { TipoRiesgoService } from 'app/entities/tipo-riesgo/tipo-riesgo.service';

describe('Component Tests', () => {
    describe('TipoRiesgo Management Delete Component', () => {
        let comp: TipoRiesgoDeleteDialogComponent;
        let fixture: ComponentFixture<TipoRiesgoDeleteDialogComponent>;
        let service: TipoRiesgoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [TipoRiesgoDeleteDialogComponent]
            })
                .overrideTemplate(TipoRiesgoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoRiesgoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoRiesgoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
