package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.actions.ExtinguishCurrentFlameAction;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

public class HauntingEcho extends AbstractHexaCard {

    public final static String ID = makeID("HauntingEcho");

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public HauntingEcho() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "HauntingEcho.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (GhostflameHelper.activeGhostFlame.charged) {
                    AbstractDungeon.actionManager.addToTop(new ChargeCurrentFlameAction());
                    AbstractDungeon.actionManager.addToTop(new ExtinguishCurrentFlameAction());
                }
            }
        });
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;

        if (GhostflameHelper.activeGhostFlame instanceof SearingGhostflame) {
            if(GhostflameHelper.activeGhostFlame.getActiveFlamesTriggerCount() == 1){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            }
        }

        if (GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame) {
            if(GhostflameHelper.activeGhostFlame.getActiveFlamesTriggerCount() + this.costForTurn >= 3){
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
            }
        }

        if(GhostflameHelper.activeGhostFlame.charged) this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}