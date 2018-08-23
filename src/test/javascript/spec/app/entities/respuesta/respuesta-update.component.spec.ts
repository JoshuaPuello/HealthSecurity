/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { RespuestaUpdateComponent } from 'app/entities/respuesta/respuesta-update.component';
import { RespuestaService } from 'app/entities/respuesta/respuesta.service';
import { Respuesta } from 'app/shared/model/respuesta.model';

describe('Component Tests', () => {
    describe('Respuesta Management Update Component', () => {
        let comp: RespuestaUpdateComponent;
        let fixture: ComponentFixture<RespuestaUpdateComponent>;
        let service: RespuestaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [RespuestaUpdateComponent]
            })
                .overrideTemplate(RespuestaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RespuestaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RespuestaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Respuesta(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.respuesta = entity;
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
                    const entity = new Respuesta();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.respuesta = entity;
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
