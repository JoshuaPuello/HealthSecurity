/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { TipoRiesgoUpdateComponent } from 'app/entities/tipo-riesgo/tipo-riesgo-update.component';
import { TipoRiesgoService } from 'app/entities/tipo-riesgo/tipo-riesgo.service';
import { TipoRiesgo } from 'app/shared/model/tipo-riesgo.model';

describe('Component Tests', () => {
    describe('TipoRiesgo Management Update Component', () => {
        let comp: TipoRiesgoUpdateComponent;
        let fixture: ComponentFixture<TipoRiesgoUpdateComponent>;
        let service: TipoRiesgoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [TipoRiesgoUpdateComponent]
            })
                .overrideTemplate(TipoRiesgoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TipoRiesgoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoRiesgoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TipoRiesgo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoRiesgo = entity;
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
                    const entity = new TipoRiesgo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tipoRiesgo = entity;
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
