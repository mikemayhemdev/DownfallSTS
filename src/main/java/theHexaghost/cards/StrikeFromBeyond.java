package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.actions.DrawSpecificAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.DrawEtherealAction;

public class StrikeFromBeyond extends AbstractHexaCard {
    public final static String ID = makeID("StrikeFromBeyond");

    public StrikeFromBeyond() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 3;
        this.tags.add(CardTags.STRIKE);
        HexaMod.loadJokeCardImage(this, "StrikeFromBeyond.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new DrawSpecificAction(1, c -> c.isEthereal));
    }


    public void triggerOnGlowCheck() {
        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c.isEthereal){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public float getTitleFontSize() {
        if(Settings.language != Settings.GameLanguage.ENG) {
            return -1.0F;
        }else {
            return 20.0F;
        }
    }
}