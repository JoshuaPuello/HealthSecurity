/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthSecurityTestModule } from '../../../test.module';
import { TipoRiesgoDetailComponent } from 'app/entities/tipo-riesgo/tipo-riesgo-detail.component';
import { TipoRiesgo } from 'app/shared/model/tipo-riesgo.model';

describe('Component Tests', () => {
    describe('TipoRiesgo Management Detail Component', () => {
        let comp: TipoRiesgoDetailComponent;
        let fixture: ComponentFixture<TipoRiesgoDetailComponent>;
        const route = ({ data: of({ tipoRiesgo: new TipoRiesgo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityTestModule],
                declarations: [TipoRiesgoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TipoRiesgoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TipoRiesgoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tipoRiesgo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
