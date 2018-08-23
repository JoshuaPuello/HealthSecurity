/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { AreaRiesgoDetailComponent } from 'app/entities/area-riesgo/area-riesgo-detail.component';
import { AreaRiesgo } from 'app/shared/model/area-riesgo.model';

describe('Component Tests', () => {
    describe('AreaRiesgo Management Detail Component', () => {
        let comp: AreaRiesgoDetailComponent;
        let fixture: ComponentFixture<AreaRiesgoDetailComponent>;
        const route = ({ data: of({ areaRiesgo: new AreaRiesgo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [AreaRiesgoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AreaRiesgoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AreaRiesgoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.areaRiesgo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
