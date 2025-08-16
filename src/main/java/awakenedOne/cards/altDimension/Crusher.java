package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.cards.AphoticFount;
import awakenedOne.util.onGenerateCardMidcombatInterface;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import hermit.util.Wiz;

@NoCompendium
@NoPools
public class Crusher extends AbstractAwakenedCard implements onGenerateCardMidcombatInterface {
    public final static String ID = AwakenedOneMod.makeID(Crusher.class.getSimpleName());

    private static final int COST = 5;
    public boolean enabled = false;
    public Crusher() {
        super(ID, COST, CardRarity.RARE, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 25;
        selfRetain = true;
        frameString = "grift";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

    }

    @Override
    public void onCreateCard(AbstractCard card) {
        if (enabled) {
            if (card != this && Wiz.p().hand.group.contains(this)) {
                if (Wiz.getLogicalCardCost(this) > 0) {
                    modifyCostForCombat(-1);
                    this.flash(Color.GREEN.cpy());
                }
            }
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY), 0.4F));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                Crusher.this.modifyCostForCombat(COST-Crusher.this.cost);
                Crusher.this.isCostModified=false;
                isDone = true;
            }
        });
    }

    public void upp() {
        upgradeDamage(5);
    }
}