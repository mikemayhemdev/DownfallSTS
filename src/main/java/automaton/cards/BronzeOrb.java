package automaton.cards;

import automaton.actions.AddToFuncAction;
import automaton.cardmods.EncodeMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.vfx.BronzeOrbEffect;

import java.util.ArrayList;

public class BronzeOrb extends AbstractBronzeCard {

    public final static String ID = makeID("BronzeOrb");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;

    public BronzeOrb() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        exhaust = true;
        isInnate = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BronzeOrbEffect(p, m), 0.5F));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> myCardsList = new ArrayList<>();
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (CardModifierManager.hasModifier(c, EncodeMod.ID)) {
                        myCardsList.add(c);
                    }
                }
                if (!myCardsList.isEmpty()) {
                    AbstractCard q = myCardsList.get(AbstractDungeon.cardRandomRng.random(0, myCardsList.size() - 1));
                    att(new AddToFuncAction(q, AbstractDungeon.player.drawPile));
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}