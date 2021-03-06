/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { TemaDeleteDialogComponent } from 'app/entities/tema/tema-delete-dialog.component';
import { TemaService } from 'app/entities/tema/tema.service';

describe('Component Tests', () => {
    describe('Tema Management Delete Component', () => {
        let comp: TemaDeleteDialogComponent;
        let fixture: ComponentFixture<TemaDeleteDialogComponent>;
        let service: TemaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [TemaDeleteDialogComponent]
            })
                .overrideTemplate(TemaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TemaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemaService);
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
