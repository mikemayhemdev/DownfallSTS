package charbosses.stances;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class EnNeutralStance extends AbstractEnemyStance {
    public static final String STANCE_ID = "Neutral";
    private static final StanceStrings stanceString;

    public EnNeutralStance() {
        this.ID = "Neutral";
        this.img = null;
        this.name = null;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }

    public void onEnterStance() {
    }

    public void render(SpriteBatch sb) {
    }

    static {
        stanceString = CardCrawlGame.languagePack.getStanceString("Neutral");
    }
}
