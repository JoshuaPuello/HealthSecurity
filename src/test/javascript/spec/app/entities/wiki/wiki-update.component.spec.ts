/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HealthSecurityTestModule } from '../../../test.module';
import { WikiUpdateComponent } from 'app/entities/wiki/wiki-update.component';
import { WikiService } from 'app/entities/wiki/wiki.service';
import { Wiki } from 'app/shared/model/wiki.model';

describe('Component Tests', () => {
    describe('Wiki Management Update Component', () => {
        let comp: WikiUpdateComponent;
        let fixture: ComponentFixture<WikiUpdateComponent>;
        let service: WikiService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [WikiUpdateComponent]
            })
                .overrideTemplate(WikiUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WikiUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WikiService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Wiki(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wiki = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Wiki();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wiki = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
