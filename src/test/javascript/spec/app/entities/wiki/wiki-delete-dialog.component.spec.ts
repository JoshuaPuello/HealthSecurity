/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HealthSecurityTestModule } from '../../../test.module';
import { WikiDeleteDialogComponent } from 'app/entities/wiki/wiki-delete-dialog.component';
import { WikiService } from 'app/entities/wiki/wiki.service';

describe('Component Tests', () => {
    describe('Wiki Management Delete Component', () => {
        let comp: WikiDeleteDialogComponent;
        let fixture: ComponentFixture<WikiDeleteDialogComponent>;
        let service: WikiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [WikiDeleteDialogComponent]
            })
                .overrideTemplate(WikiDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WikiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WikiService);
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
