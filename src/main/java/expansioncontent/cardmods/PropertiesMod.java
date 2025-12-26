package expansioncontent.cardmods;

import awakenedOne.actions.ConjureAction;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import downfall.downfallMod;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;
import java.util.List;

import static awakenedOne.util.Wiz.atb;
import static expansioncontent.expansionContentMod.ECHO;

@AbstractCardModifier.SaveIgnore
public class PropertiesMod extends AbstractCardModifier {
    //This Modifier isn't saved due to how much it relies on Array lists to work, if you want to do something that survives Save and Exit, use a more specific card mod from this package.

    public static String ID = "downfall:PropertiesMod";
    public static final UIStrings uiStrings =  CardCrawlGame.languagePack.getUIString(ID);

    private static final TextureAtlas.AtlasRegion exhaustIcon = expansionContentMod.UIAtlas.findRegion("exhaustIcon");
    private static final TextureAtlas.AtlasRegion etherealIcon = expansionContentMod.UIAtlas.findRegion("etherealIcon");
    private static final TextureAtlas.AtlasRegion retainIcon = expansionContentMod.UIAtlas.findRegion("retainIcon");
    private static final TextureAtlas.AtlasRegion unplayableIcon = expansionContentMod.UIAtlas.findRegion("unplayableIcon");

    public ArrayList<supportedProperties> bonusProperties;
    public ArrayList<supportedProperties> bonusPropertiesForThisTurn;


    public PropertiesMod() {
        bonusProperties = new ArrayList<>();
        bonusPropertiesForThisTurn = new ArrayList<>();
    }

    public PropertiesMod(supportedProperties property, boolean onlyForThisTurn) {
        this();
        if (onlyForThisTurn)
            bonusPropertiesForThisTurn.add(property);
        else
            bonusProperties.add(property);
    }

    //Important make sure that cards that apply keywords don't apply them only to cards that already have them, especially true for this turn effects.
    public void addProperty(supportedProperties property, boolean onlyForThisTurn) {
        if (onlyForThisTurn) {
            if (bonusProperties.contains(property) || bonusPropertiesForThisTurn.contains(property))
                return;
            bonusPropertiesForThisTurn.add(property);
        } else if (!bonusProperties.contains(property))
            bonusProperties.add(property);
    }

    //I think that it is the best way to make sure that all of the properties are applied.
    private void applyProperties(AbstractCard card) {
        if (bonusProperties.contains(supportedProperties.ECHO))
            if (!card.hasTag(ECHO))
                card.tags.add(ECHO);

        if (bonusProperties.contains(supportedProperties.ETHEREAL) || bonusPropertiesForThisTurn.contains(supportedProperties.ETHEREAL)) {
            card.isEthereal = true;
        }

        if (bonusProperties.contains(supportedProperties.EXHAUST) || bonusPropertiesForThisTurn.contains(supportedProperties.EXHAUST)) {
            card.exhaust = true;
        }

        if (bonusProperties.contains(supportedProperties.RETAIN)) {
            card.selfRetain = true;
        } else if (bonusPropertiesForThisTurn.contains(supportedProperties.RETAIN)) {
            card.retain = true;
        }

        if (bonusProperties.contains(supportedProperties.UNPLAYABLE) || bonusPropertiesForThisTurn.contains(supportedProperties.UNPLAYABLE)) {
            if (!card.tags.contains(expansionContentMod.UNPLAYABLE))
                card.tags.add(expansionContentMod.UNPLAYABLE);
        }

        card.initializeDescription(); //Making sure stuff is in description
    }

    //I have no idea how LibGDX sb.draw works, so if there is a better way to write this code feel free to do update this method.
//    private float renderIcon(AbstractCard card, SpriteBatch sb, TextureAtlas.AtlasRegion icon, float offset) {
//        float cardScale = Settings.scale * card.drawScale * 0.45f; //0.45 because I want some space from the size, but technically 0.5 could also work.
//        float iconScale = Settings.scale * card.drawScale * 0.35f; //0.35 is to get the icon to reasonable size. Note: changing the dimensions of files affects how big they are.
//
//        sb.draw( icon,
//                card.current_x + AbstractCard.RAW_W * cardScale + (icon.offsetX - (float) icon.originalWidth) * iconScale,
//                card.current_y + AbstractCard.RAW_H * cardScale + (icon.offsetY - (float) icon.originalHeight) * iconScale + offset,
//                (icon.originalWidth - icon.offsetX) * iconScale - AbstractCard.RAW_W * cardScale, //Center of a card, to rotate around with the card.
//                (icon.originalHeight - icon.offsetY) * iconScale - AbstractCard.RAW_H * cardScale - offset,
//                (float) icon.packedWidth * iconScale, //Render scale
//                (float) icon.packedHeight * iconScale,
//                1, //Don't touch this scale, as it also to distance from origin.
//                1,
//                card.angle);
//
//        return icon.originalHeight * iconScale; //Returns height offset for the next icon;
//    }

//    @Override
//    public void onRender(AbstractCard card, SpriteBatch sb) {
//        if (!downfallMod.useIconsForAppliedProperties)
//            return;
//
//        Color color = Color.WHITE.cpy();
//        color.a = card.transparency;
//        sb.setColor(color);
//
//        //TODO: Add overlay(?)/mask(?) for Echo cards.
//
//        float offset = 0;
//
//        //Rendering Icons in order of importance
//        if (bonusProperties.contains(supportedProperties.UNPLAYABLE) || bonusPropertiesForThisTurn.contains(supportedProperties.UNPLAYABLE)) {
//            offset -= renderIcon(card, sb, unplayableIcon, offset);
//        }
//
//        if (bonusProperties.contains(supportedProperties.ETHEREAL) || bonusPropertiesForThisTurn.contains(supportedProperties.ETHEREAL)) {
//            offset -= renderIcon(card, sb, etherealIcon, offset);
//        }
//
//        if (bonusProperties.contains(supportedProperties.RETAIN) || bonusPropertiesForThisTurn.contains(supportedProperties.RETAIN)) {
//            offset -= renderIcon(card, sb, retainIcon, offset);
//        }
//
//        if (bonusProperties.contains(supportedProperties.EXHAUST) || bonusPropertiesForThisTurn.contains(supportedProperties.EXHAUST)) {
//            renderIcon(card, sb, exhaustIcon, offset);
//        }
//    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (!downfallMod.useIconsForAppliedProperties)
            return null;

        if (bonusProperties.isEmpty() && bonusPropertiesForThisTurn.isEmpty())
            return null;

        StringBuilder description = new StringBuilder();

        if (!bonusProperties.isEmpty()) {
            description.append(uiStrings.TEXT[1]);
            keywordBuilder(description, bonusProperties);

            if (!bonusPropertiesForThisTurn.isEmpty()) {
                description.append(uiStrings.TEXT[3]);
                keywordBuilder(description, bonusPropertiesForThisTurn);
            }
        } else {
            description.append(uiStrings.TEXT[2]);
            keywordBuilder(description, bonusPropertiesForThisTurn);
        }


        TooltipInfo frankensteinKeyword = new TooltipInfo(uiStrings.TEXT[0], description.toString());
        List<TooltipInfo> list = new ArrayList<>();
        list.add(frankensteinKeyword);
        return list;
    }

    private static void keywordBuilder(StringBuilder description, ArrayList<supportedProperties> properties) {
        int counter = 0;

        if (properties.contains(supportedProperties.ECHO)) {
            description.append(uiStrings.TEXT[6]);
            description.append(uiStrings.TEXT[7]);
            counter++;
            if (properties.size() > counter)
                description.append(uiStrings.TEXT[4]);
            else {
                description.append(uiStrings.TEXT[5]);
                return;
            }
        }

        if (properties.contains(supportedProperties.UNPLAYABLE)) {
            description.append(uiStrings.TEXT[6]);
            description.append(uiStrings.TEXT[8]);
            counter++;
            if (properties.size() > counter)
                description.append(uiStrings.TEXT[4]);
            else {
                description.append(uiStrings.TEXT[5]);
                return;
            }
        }
        if (!properties.contains(supportedProperties.ECHO)) {
            if (properties.contains(supportedProperties.ETHEREAL)) {

                description.append(uiStrings.TEXT[6]);
                description.append(uiStrings.TEXT[9]);
                counter++;
                if (properties.size() > counter)
                    description.append(uiStrings.TEXT[4]);
                else {
                    description.append(uiStrings.TEXT[5]);
                    return;
                }
            }
        }

        if (!properties.contains(supportedProperties.ECHO)) {
            if (properties.contains(supportedProperties.RETAIN)) {
                description.append(uiStrings.TEXT[6]);
                description.append(uiStrings.TEXT[10]);
                counter++;
                if (properties.size() > counter)
                    description.append(uiStrings.TEXT[4]);
                else {
                    description.append(uiStrings.TEXT[5]);
                    return;
                }
            }
        }

        if (!properties.contains(supportedProperties.ECHO)) {
            if (properties.contains(supportedProperties.EXHAUST)) {
                description.append(uiStrings.TEXT[6]);
                description.append(uiStrings.TEXT[11]);
                description.append(uiStrings.TEXT[5]);
            }
        }
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (downfallMod.useIconsForAppliedProperties)
//            return rawDescription;

        StringBuilder thingsToAdd = new StringBuilder();

        if (bonusProperties.contains(supportedProperties.UNPLAYABLE) || bonusPropertiesForThisTurn.contains(supportedProperties.UNPLAYABLE)) {
            thingsToAdd.append(uiStrings.TEXT[8]); //"Unplayable"
            thingsToAdd.append(uiStrings.TEXT[5]); //"."
            thingsToAdd.append(uiStrings.TEXT[12]); //" "
        }

        if (!bonusProperties.contains(supportedProperties.ECHO)) {
            if (bonusProperties.contains(supportedProperties.ETHEREAL) || bonusPropertiesForThisTurn.contains(supportedProperties.ETHEREAL)) {
                thingsToAdd.append(uiStrings.TEXT[9]); //ETHEREAL
                thingsToAdd.append(uiStrings.TEXT[5]); //"."
                thingsToAdd.append(uiStrings.TEXT[12]); //" "
            }
        }

        if (bonusProperties.contains(supportedProperties.RETAIN) || bonusPropertiesForThisTurn.contains(supportedProperties.RETAIN)) {
            thingsToAdd.append(uiStrings.TEXT[10]); //RETAIN
            thingsToAdd.append(uiStrings.TEXT[5]); //"."
            thingsToAdd.append(uiStrings.TEXT[12]); //" "
        }

        if (thingsToAdd.length() > 0) {
            thingsToAdd.append(uiStrings.TEXT[13]); //"NL "
            rawDescription = thingsToAdd + rawDescription;
        }

        if (!bonusProperties.contains(supportedProperties.ECHO)) {
            if (bonusProperties.contains(supportedProperties.EXHAUST) || bonusPropertiesForThisTurn.contains(supportedProperties.EXHAUST)) {
                thingsToAdd.append(uiStrings.TEXT[10]);
                rawDescription = rawDescription
                        + uiStrings.TEXT[12] //" "
                        + uiStrings.TEXT[13] //"NL "
                        + uiStrings.TEXT[11] //"Exhaust"
                        + uiStrings.TEXT[5]; //"."
            }
        }

        if (bonusProperties.contains(supportedProperties.ECHO) || bonusPropertiesForThisTurn.contains(supportedProperties.ECHO)) {
            thingsToAdd.append(uiStrings.TEXT[6]); //#y
            thingsToAdd.append(uiStrings.TEXT[7]); //ECHO
            thingsToAdd.append(uiStrings.TEXT[5]); //"."
            thingsToAdd.append(uiStrings.TEXT[12]); //" "
        }

        return rawDescription;
    }

    //This should be removed as soon as Echo gets a visual representation on the card.
    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return bonusProperties.contains(supportedProperties.ECHO) ? uiStrings.TEXT[14] + cardName : cardName;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(card, ID);
        if (!mods.isEmpty() && mods.get(0) instanceof PropertiesMod) {
            for (supportedProperties p: bonusPropertiesForThisTurn)
                ((PropertiesMod) mods.get(0)).addProperty(p, true);
            for (supportedProperties p: bonusProperties)
                ((PropertiesMod) mods.get(0)).addProperty(p, false);
            ((PropertiesMod) mods.get(0)).applyProperties(card);
            return false;
        }
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        applyProperties(card);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if (bonusProperties.contains(supportedProperties.UNPLAYABLE)) {
            card.cantUseMessage = CardCrawlGame.languagePack.getUIString(UnplayableMod.ID).TEXT[1];
            return false;
        }

        if (bonusPropertiesForThisTurn.contains(supportedProperties.UNPLAYABLE)) {
            card.cantUseMessage = CardCrawlGame.languagePack.getUIString(UnplayableMod.ID).TEXT[2];
            return false;
        }
        return true;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        //Slightly delaying the effect to let game to end the turn, and discard the hand.
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                if (bonusPropertiesForThisTurn.remove(supportedProperties.ETHEREAL))
                                    if (!bonusProperties.contains(supportedProperties.ETHEREAL))
                                        card.isEthereal = false;

                                if (bonusPropertiesForThisTurn.remove(supportedProperties.EXHAUST))
                                    if (!bonusProperties.contains(supportedProperties.EXHAUST))
                                        card.exhaust = false;

                                if (bonusPropertiesForThisTurn.remove(supportedProperties.RETAIN))
                                        card.retain = false;

                                if (bonusPropertiesForThisTurn.remove(supportedProperties.UNPLAYABLE))
                                    if (!bonusProperties.contains(supportedProperties.UNPLAYABLE))
                                        card.tags.remove(expansionContentMod.UNPLAYABLE);

                                card.initializeDescription(); //Cleaning this
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public AbstractCardModifier makeCopy() {
        PropertiesMod copy = new PropertiesMod();
        copy.bonusProperties.addAll(this.bonusProperties);
        copy.bonusPropertiesForThisTurn.addAll(this.bonusPropertiesForThisTurn);
        return copy;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    //Retain Override - do not remove - even if it is technically different from how the game usually works, I will do what I am allowed to force the mechanics to work as they are described - Stanek
    //sorry - blue

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        if ((card.hasTag(ECHO))) {
            card.exhaust = true;
        }
    }

   @Override
    public void onRetained(AbstractCard card) {
       if ((card.isEthereal) || (card.hasTag(ECHO))) {
           AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
       }
   }

    public enum supportedProperties {
        ECHO,
        ETHEREAL,
        EXHAUST,
        RETAIN,
        UNPLAYABLE
    }
}
