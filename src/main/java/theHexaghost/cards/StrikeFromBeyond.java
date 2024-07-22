package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.DrawEtherealAction;

public class StrikeFromBeyond extends AbstractHexaCard {

    public final static String ID = makeID("StrikeFromBeyond");

    private static final int DAMAGE = 4;
    private static final int MAGIC = 2;
    private static final int UPG_DAMAGE = 3;

    public StrikeFromBeyond() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
        this.tags.add(CardTags.STRIKE);
        HexaMod.loadJokeCardImage(this, "StrikeFromBeyond.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new DrawEtherealAction(1));
    }


    public void triggerOnGlowCheck() {
        boolean has_ethereal_in_draw_pile = false;
        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c.isEthereal){
                has_ethereal_in_draw_pile = true;
                break;
            }
        }
        this.glowColor = has_ethereal_in_draw_pile ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
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