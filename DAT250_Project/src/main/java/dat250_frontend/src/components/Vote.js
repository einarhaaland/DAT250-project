import React, { useState } from 'react';

export function Vote() {
    return (
        <div>
            <label> Question </label>
            <button type="submit">YES</button>
            <button type="submit">NO</button>
        </div>
    )
}