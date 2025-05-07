package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.cards.Strike;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.actions.EchoACardAction;
import expansioncontent.expansionContentMod;
import hermit.cards.Strike_Hermit;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class Sixitude extends CustomRelic {

    public static final String ID = HexaMod.makeID("Sixitude");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Sixitude.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Sixitude.png"));

    //todo: set variable for # of required cards to play

    public Sixitude() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!targetCard.hasTag(expansionContentMod.ECHO)) {
            ++this.counter;
            if (this.counter % 6 == 0) {
                this.flash();
                this.counter = 0;
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                // this.addToBot(new DamageRandomEnemyAction(new DamageInfo(null, 6, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                AbstractCard c2 = new Strike_Hermit();
                addToBot(new EchoACardAction(c2, true));
            }
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
