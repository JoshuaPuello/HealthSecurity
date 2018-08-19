/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HealthSecurityTestModule } from '../../../test.module';
import { AreaRiesgoUpdateComponent } from 'app/entities/area-riesgo/area-riesgo-update.component';
import { AreaRiesgoService } from 'app/entities/area-riesgo/area-riesgo.service';
import { AreaRiesgo } from 'app/shared/model/area-riesgo.model';

describe('Component Tests', () => {
    describe('AreaRiesgo Management Update Component', () => {
        let comp: AreaRiesgoUpdateComponent;
        let fixture: ComponentFixture<AreaRiesgoUpdateComponent>;
        let service: AreaRiesgoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [AreaRiesgoUpdateComponent]
            })
                .overrideTemplate(AreaRiesgoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AreaRiesgoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AreaRiesgoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AreaRiesgo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.areaRiesgo = entity;
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
                    const entity = new AreaRiesgo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.areaRiesgo = entity;
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
