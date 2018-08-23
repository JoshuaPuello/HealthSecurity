/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { AreaRiesgoDeleteDialogComponent } from 'app/entities/area-riesgo/area-riesgo-delete-dialog.component';
import { AreaRiesgoService } from 'app/entities/area-riesgo/area-riesgo.service';

describe('Component Tests', () => {
    describe('AreaRiesgo Management Delete Component', () => {
        let comp: AreaRiesgoDeleteDialogComponent;
        let fixture: ComponentFixture<AreaRiesgoDeleteDialogComponent>;
        let service: AreaRiesgoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [AreaRiesgoDeleteDialogComponent]
            })
                .overrideTemplate(AreaRiesgoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AreaRiesgoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AreaRiesgoService);
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
