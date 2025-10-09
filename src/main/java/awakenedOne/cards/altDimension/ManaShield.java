package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.Collections;

import static awakenedOne.util.Wiz.atb;

@NoCompendium
@NoPools
public class ManaShield extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(ManaShield.class.getSimpleName());

    public ManaShield() {
        super(ID, 2, CardRarity.RARE, CardType.SKILL, CardTarget.SELF);
        baseBlock = 14;
        tags.add(CardTags.HEALING);

        frameString = "gordian";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png", "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ConjureAction(false));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> handcopy = new ArrayList<>();
                handcopy.addAll(AbstractDungeon.player.hand.group);
                Collections.shuffle(handcopy);
                for (AbstractCard c : handcopy) {
                    if (c instanceof AbstractSpellCard) {
                        c.modifyCostForCombat(-1);
                        return;
                    }
                }
            }
        });
    }

    public void upp() {
        upgradeBlock(4);
    }
}