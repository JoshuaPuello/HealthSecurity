import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';

@Component({
    selector: 'jhi-persona-update',
    templateUrl: './persona-update.component.html'
})
export class PersonaUpdateComponent implements OnInit {
    private _persona: IPersona;
    isSaving: boolean;
    nacimientoDp: any;

    constructor(private personaService: PersonaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ persona }) => {
            this.persona = persona;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.persona.id !== undefined) {
            this.subscribeToSaveResponse(this.personaService.update(this.persona));
        } else {
            this.subscribeToSaveResponse(this.personaService.create(this.persona));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>) {
        result.subscribe((res: HttpResponse<IPersona>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get persona() {
        return this._persona;
    }

    set persona(persona: IPersona) {
        this._persona = persona;
    }
}
