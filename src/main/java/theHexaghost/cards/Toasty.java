package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import sneckomod.SneckoMod;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.*;
import theHexaghost.ghostflames.CrushingGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;
import theHexaghost.ghostflames.SearingGhostflame;

import java.util.ArrayList;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Toasty extends AbstractHexaCard implements OctopusCard {

    public final static String ID = makeID("FlareFlick");

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    //Flare Flick
    public Toasty() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        HexaMod.loadJokeCardImage(this, "Toasty.png");
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public ArrayList<OctoChoiceCard> choiceList() {

        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoRetract", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoNothing", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[2]));
        cardList.add(new OctoChoiceCard("octo:OctoAdvance", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[0]));

        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoAdvance":
                atb(new AdvanceAction(false));
                break;
            case "octo:OctoRetract":
                atb(new RetractAction());
                break;
            case "octo:OctoNothing":
                break;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
//        atb(new BurningHitAction(m, p, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new ExtinguishCurrentFlameAction());
        atb(new ChargeCurrentFlameAction());
        if (upgraded) {
            atb(new OctoChoiceAction(m, this));
        } else {
            atb(new AdvanceAction(false));
        }
    }


    @Override
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

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}