/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthSecurityWebTestModule } from '../../../test.module';
import { WikiDetailComponent } from 'app/entities/wiki/wiki-detail.component';
import { Wiki } from 'app/shared/model/wiki.model';

describe('Component Tests', () => {
    describe('Wiki Management Detail Component', () => {
        let comp: WikiDetailComponent;
        let fixture: ComponentFixture<WikiDetailComponent>;
        const route = ({ data: of({ wiki: new Wiki(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HealthSecurityWebTestModule],
                declarations: [WikiDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WikiDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WikiDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.wiki).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
