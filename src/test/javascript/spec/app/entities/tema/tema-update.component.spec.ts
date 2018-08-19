/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HealthSecurityTestModule } from '../../../test.module';
import { TemaUpdateComponent } from 'app/entities/tema/tema-update.component';
import { TemaService } from 'app/entities/tema/tema.service';
import { Tema } from 'app/shared/model/tema.model';

describe('Component Tests', () => {
    describe('Tema Management Update Component', () => {
        let comp: TemaUpdateComponent;
        let fixture: ComponentFixture<TemaUpdateComponent>;
        let service: TemaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [TemaUpdateComponent]
            })
                .overrideTemplate(TemaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TemaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Tema(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tema = entity;
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
                    const entity = new Tema();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tema = entity;
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
